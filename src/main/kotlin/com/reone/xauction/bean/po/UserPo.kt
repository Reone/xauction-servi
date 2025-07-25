package com.reone.xauction.bean.po

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

/**
 *
 * @author reone
 * @date 2025/7/17 16:43
 */
@Entity
@Table(name = "xa_user")
@DynamicUpdate
open class UserPo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,

    @Column(nullable = false, unique = true)
    open var code: String? = null,

    @Column(nullable = true)
    open var name: String? = null,

    @Column(nullable = true)
    open var nick: String? = null,

    @Column(nullable = true)
    open var phone: String? = null,

    @Column(nullable = true)
    open var avatar: String? = null,

    @Column(nullable = true)
    open var loginTime: Long? = null,

    @Column(nullable = true)
    open var password: String? = null,

    /**
     * 角色 0:普通用户 1:管理员 -1:超级用户
     */
    @Column(nullable = true, columnDefinition = "int default 0")
    open var role: Int? = null,


    ) : BasePo() {
    fun copyNotNull(form: UserPo) {
        form.name?.let { name = it }
        form.phone?.let { phone = it }
        form.nick?.let { nick = it }
        form.avatar?.let { avatar = it }
        form.password?.let { password = it }
        form.loginTime?.let { loginTime = it }
        form.role?.let { role = it }
    }
}