
### GIT 提交规范
- feat (Feature): 新增功能
- fix (Fix): 修复错误
- style (Style): 表示代码样式修改的提交，不影响程序逻辑。
- revert: 回滚之前的提交
- build: 构建系统或外部依赖项的变更
- refactor (Refactor): 代码重构，即不改变代码功能的情况下优化代码结构。
- docs (Documentation): 文档更新
- test (Test): 测试相关的更改
- chore (Chore): 维护任务，如更新依赖项、配置文件等。
- perf (Performance): 性能优化
- ci (Continuous Integration): 持续集成相关的更改
---
### 实体类后缀
- Po:  持久层,直接对应数据库表
- Dto: 传输层,跨系统/接口数据传输 (Controller接收)
- Vo:  展示层,前端数据展示 (Controller返回)
- Bo:  业务层,封装复杂业务逻辑 (Service接收返回)
- Bean:通用,通用数据容器
---
### 类名后缀
- Api : 接口
- Service : 业务逻辑层
- Dao : 数据访问层
- Repository : 数据访问对象
- Helper : 业务辅助类
- Ex : 某一对象相关的工具类