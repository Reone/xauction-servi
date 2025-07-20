# -*- coding: utf-8 -*-

import paramiko
import os
import shutil
import subprocess

# 执行本地命令
subprocess.call(["./gradlew", "clean"])
subprocess.call(["./gradlew", "build"])

# 创建目录
docker_dir = "./docker_dir"
if os.path.exists(docker_dir):
    shutil.rmtree(docker_dir)  # 删除已存在的目录及其内容
os.makedirs(docker_dir)  # 创建新的目录

# 拷贝文件
shutil.copy("Dockerfile", docker_dir)
# 获取libs文件夹下结尾为SNAPSHOT.jar的文件列表
snapshot_jars = [f for f in os.listdir("build/libs/") if f.endswith("SNAPSHOT.jar")]
for jar_file in snapshot_jars:
    shutil.copy(os.path.join("build/libs/", jar_file), docker_dir)

print("Files copied successfully.")

# 准备上传的目录名
dir_name = "docker_dir"
# 目标远程目录
remote_dir = "/home/xauction/"
# docker名字
image_name = "xauction"

# 本地文件夹路径
local_folder_path = "./{}/".format(dir_name)
# 压缩后的文件路径
archive_file_path = "./{}.tar.gz".format(dir_name)
# 远程服务器信息
remote_user = "root"
remote_host = "58.87.80.62"

# 压缩本地文件夹
shutil.make_archive(dir_name, "gztar", local_folder_path)

# 创建SSH客户端
ssh_client = paramiko.SSHClient()
ssh_client.set_missing_host_key_policy(paramiko.AutoAddPolicy())

try:
    # 连接远程服务器
    ssh_client.connect(hostname=remote_host, username=remote_user)

    # 创建SFTP客户端
    sftp_client = ssh_client.open_sftp()

    # 上传本地压缩文件到远程服务器
    sftp_client.put(archive_file_path, os.path.join(remote_dir, "{}.tar.gz".format(dir_name)))

    # 关闭SFTP客户端连接
    sftp_client.close()

    print("sftp upload successfully")

    # 执行远程命令解压文件
    stdin, stdout, stderr = ssh_client.exec_command("cd {} && rm -rf {} && mkdir {} && tar -xzvf {}.tar.gz -C {}".format(remote_dir, dir_name, dir_name, dir_name, dir_name))
    print(stdout.read().decode())

    print("tar successfully")
finally:
    # 关闭SSH客户端连接
    ssh_client.close()
    # 清理本地临时文件
    os.remove(archive_file_path)

# 构建ssh前缀
ssh_pre = "ssh {}@{}".format(remote_user, remote_host)

# 停止正在运行的kservi镜像
ssh_command = "{} docker stop {}".format(ssh_pre, image_name)
print(ssh_command)
subprocess.call(ssh_command, shell=True)

# 删除停止的kservi镜像
ssh_command = "{} docker rm {}".format(ssh_pre, image_name)
print(ssh_command)
subprocess.call(ssh_command, shell=True)

# 删除本地镜像
ssh_command = "{} docker rmi {}".format(ssh_pre, image_name)
print(ssh_command)
subprocess.call(ssh_command, shell=True)

# 打包镜像
ssh_command = "{} docker build -t {} {}{}".format(ssh_pre, image_name, remote_dir, dir_name)
print(ssh_command)
build_out = subprocess.check_output(ssh_command, shell=True)
print(build_out)

# 运行kservi镜像
ssh_command = "{} docker run -d --name {} -p 8080:8080 {}".format(ssh_pre, image_name, image_name)
print(ssh_command)
subprocess.call(ssh_command, shell=True)

print("deploy successfully.")
