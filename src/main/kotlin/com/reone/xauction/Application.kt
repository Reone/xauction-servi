package com.reone.xauction

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    println("""
        (♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  
         _____   _____   ___    _   _   _____ 
        | ___ \ |  ___| / _ \  | \ | | | ____|
        | |_/ / | |___ | | | | |  \| | | |___  
        |    /  |  ___|| | | | | |\  | |  ___|
        | |\ \  | |___ | |_| | | | \ | | |___
        \_| \_| |_____| \___/  |_|  \| |_____|       
    """.trimIndent())
}
