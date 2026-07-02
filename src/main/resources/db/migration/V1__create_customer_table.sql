CREATE TABLE customers (
    id UUID NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    document VARCHAR(20) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    gender VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT pk_customers PRIMARY KEY (id),
    CONSTRAINT uk_customers_document UNIQUE (document),
    CONSTRAINT uk_customers_email UNIQUE (email)
);