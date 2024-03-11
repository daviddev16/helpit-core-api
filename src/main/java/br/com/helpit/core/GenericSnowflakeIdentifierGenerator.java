package br.com.helpit.core;

import de.mkammerer.snowflakeid.SnowflakeIdGenerator;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public abstract class GenericSnowflakeIdentifierGenerator implements IdentifierGenerator {

    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public void initialize(SqlStringGenerationContext context) {
        this.snowflakeIdGenerator = SnowflakeIdGenerator.createDefault(workerId());
    }

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return snowflakeIdGenerator.next();
    }

    public abstract int workerId();

}
