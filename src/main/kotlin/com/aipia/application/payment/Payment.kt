package com.aipia.application.payment

import com.aipia.application.member.Member
import com.aipia.application.order.Order
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "payments",
    uniqueConstraints = [UniqueConstraint(name = "UNI_MEMBER_ORDER", columnNames = ["member_id", "order_id"])],
    indexes = [Index(name = "IDX_MEMBER_STATUS", columnList = "member_id, status")]
)
class Payment(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0,

    @Column(name = "member_id")
    val memberId: String,

    @Column(name = "order_id")
    val orderId: Long,

    val amount: Int = 0,

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
        name = "member_id",
        referencedColumnName = "id",
        insertable = false,
        updatable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    var member: Member? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
        name = "order_id",
        referencedColumnName = "id",
        insertable = false,
        updatable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    var order: Order? = null,
) {
    @Column(name = "status")
    var status: PaymentStatus = PaymentStatus.SUCCESS
        protected set

    var failMessage: String? = null
        protected set

    fun fail(failMessage: String?) {
        this.status = PaymentStatus.FAILED
        this.failMessage = failMessage
    }
}