package org.collaborator.paymentlab.common.inmemory.core

class IdGeneratorFactory {
    companion object {
        fun <T> create(idClass: Class<T>): IdGenerator<T> {
            if (idClass.name == Long::class.java.name)
                return SequentialLongIdGenerator() as IdGenerator<T>
            else throw UnsupportedOperationException()
        }
    }
}