CREATE TABLE CUSTOMER (
  C_CUSTKEY    INTEGER,
  C_NAME       VARCHAR(25) NOT NULL,
  C_ADDRESS    VARCHAR(40) NOT NULL,
  C_CITY       VARCHAR(10) NOT NULL,
  C_NATION     VARCHAR(15) NOT NULL,
  C_REGION     VARCHAR(12) NOT NULL,
  C_PHONE      VARCHAR(15) NOT NULL,
  C_MKTSEGMENT VARCHAR(10) NOT NULL
);

CREATE TABLE DATES (
  D_DATEKEY          INTEGER,
  D_DATE             VARCHAR(18) NOT NULL,
  D_DAYOFWEEK        VARCHAR(8)  NOT NULL,
  D_MONTH            VARCHAR(9)  NOT NULL,
  D_YEAR             INTEGER     NOT NULL,
  D_YEARMONTHNUM     INTEGER,
  D_YEARMONTH        VARCHAR(7)  NOT NULL,
  D_DAYNUMINWEEK     INTEGER,
  D_DAYNUMINMONTH    INTEGER,
  D_DAYNUMINYEAR     INTEGER,
  D_MONTHNUMINYEAR   INTEGER,
  D_WEEKNUMINYEAR    INTEGER,
  D_SELLINGSEASON    VARCHAR(12) NOT NULL,
  D_LASTDAYINWEEKFL  INTEGER,
  D_LASTDAYINMONTHFL INTEGER,
  D_HOLIDAYFL        INTEGER,
  D_WEEKDAYFL        INTEGER
);

CREATE TABLE PART (
  P_PARTKEY   INTEGER,
  P_NAME      VARCHAR(22) NOT NULL,
  P_MFGR      VARCHAR(6)  NOT NULL,
  P_CATEGORY  VARCHAR(7)  NOT NULL,
  P_BRAND     VARCHAR(9)  NOT NULL,
  P_COLOR     VARCHAR(11) NOT NULL,
  P_TYPE      VARCHAR(25) NOT NULL,
  P_SIZE      INTEGER     NOT NULL,
  P_CONTAINER VARCHAR(10) NOT NULL
);

CREATE TABLE SUPPLIER (
  S_SUPPKEY INTEGER,
  S_NAME    VARCHAR(25) NOT NULL,
  S_ADDRESS VARCHAR(25) NOT NULL,
  S_CITY    VARCHAR(10) NOT NULL,
  S_NATION  VARCHAR(15) NOT NULL,
  S_REGION  VARCHAR(12) NOT NULL,
  S_PHONE   VARCHAR(15) NOT NULL
);

CREATE TABLE LINEORDER (
  LO_ORDERKEY      INTEGER,
  LO_LINENUMBER    INTEGER,
  LO_CUSTKEY       INTEGER     NOT NULL,
  LO_PARTKEY       INTEGER     NOT NULL,
  LO_SUPPKEY       INTEGER     NOT NULL,
  LO_ORDERDATE     INTEGER     NOT NULL,
  LO_ORDERPRIORITY VARCHAR(15) NOT NULL,
  LO_SHIPPRIORITY  INTEGER,
  LO_QUANTITY      INTEGER,
  LO_EXTENDEDPRICE INTEGER,
  LO_ORDTOTALPRICE INTEGER,
  LO_DISCOUNT      INTEGER,
  LO_REVENUE       INTEGER,
  LO_SUPPLYCOST    INTEGER,
  LO_TAX           INTEGER,
  LO_COMMITDATE    INTEGER     NOT NULL,
  LO_SHIPMODE      VARCHAR(10) NOT NULL
);


-- query for explain the plan of the query
EXPLAIN PLAN FOR SELECT * FROM DATES; 

EXPLAIN PLAN FOR
select
    d_year, s_city, p_brand,
    sum(lo_revenue - lo_supplycost) as profit
from
    DATES, CUSTOMER, SUPPLIER, PART, LINEORDER
where
    lo_custkey = c_custkey
    and lo_suppkey = s_suppkey
    and lo_partkey = p_partkey
    and lo_orderdate = d_datekey
    and s_nation = 'UNITED STATES'
    and (d_year = 1997 or d_year = 1998)
    and p_category = 'MFGR#14'
group by
    d_year, s_city, p_brand
order by
    d_year, s_city, p_brand;

--see the output of the explain
SELECT PLAN_TABLE_OUTPUT FROM TABLE(DBMS_XPLAN.DISPLAY());

-- adding optimization indexies
CREATE INDEX LO_CUSTKEY_IDX ON LINEORDER(LO_CUSTKEY);
CREATE INDEX LO_PARTKEY_IDX ON LINEORDER(LO_PARTKEY);
CREATE INDEX LO_SUPPKEY_IDX ON LINEORDER(LO_SUPPKEY);
CREATE INDEX LO_ORDERDATE_IDX ON LINEORDER(LO_ORDERDATE);
CREATE INDEX LO_COMMITDATE_IDX ON LINEORDER(LO_COMMITDATE);

CREATE INDEX C_REGION_IDX ON CUSTOMER(C_REGION);
CREATE INDEX C_NAITON_IDX ON CUSTOMER(C_NATION);
CREATE INDEX C_CITY_IDX ON CUSTOMER(C_CITY);

CREATE INDEX D_YEAR_IDX ON DATES(D_YEAR);
CREATE INDEX D_YEARMONTHNUM_IDX ON DATES(D_YEARMONTHNUM);
CREATE INDEX D_WEEKNUMINYEAR_IDX ON DATES(D_WEEKNUMINYEAR);
CREATE INDEX D_YEARMONTH_IDX ON DATES(D_YEARMONTH);


CREATE INDEX P_CATEGORY_IDX ON PART(P_BRAND);
CREATE INDEX P_BRAND_IDX ON PART(P_BRAND);
CREATE INDEX P_MFGR_IDX ON PART(P_BRAND);

CREATE INDEX S_REGION_IDX ON SUPPLIER(S_REGION);
CREATE INDEX S_NAITON_IDX ON SUPPLIER(S_NATION);
CREATE INDEX S_CITY_IDX ON SUPPLIER(S_CITY);


CREATE INDEX LO_QUANTITY_IDX ON LINEORDER(LO_QUANTITY);
CREATE INDEX LO_DISCOUNT_IDX ON LINEORDER(LO_DISCOUNT);

-- we can create our Plan tale using the folliwng query

CREATE TABLE my_plan_table(
  STATEMENT_ID      VARCHAR2(30),
  PLAN_ID           NUMBER,
  TIMESTAMP         DATE,
  REMARKS           VARCHAR2(4000),
  OPERATION         VARCHAR2(30),
  OPTIONS           VARCHAR2(255),
  OBJECT_NODE       VARCHAR2(128),
  OBJECT_OWNER      VARCHAR2(30),
  OBJECT_NAME       VARCHAR2(30),
  OBJECT_ALIAS      VARCHAR2(65),
  OBJECT_INSTANCE   NUMBER,
  OBJECT_TYPE       VARCHAR2(30),
  OPTIMIZER         VARCHAR2(255),
  SEARCH_COLUMNS    NUMBER,
  ID                NUMBER,
  PARENT_ID         NUMBER,
  DEPTH             NUMBER,
  POSITION          NUMBER,
  COST              NUMBER,
  CARDINALITY       NUMBER,
  BYTES             NUMBER,
  OTHER_TAG         VARCHAR2(255),
  PARTITION_START   VARCHAR2(255),
  PARTITION_STOP    VARCHAR2(255),
  PARTITION_ID      NUMBER,
  OTHER             LONG,
  OTHER_XML         CLOB,
  DISTRIBUTION      VARCHAR2(30),
  CPU_COST          NUMBER,
  IO_COST           NUMBER,
  TEMP_SPACE        NUMBER,
  ACCESS_PREDICATES VARCHAR2(4000),
  FILTER_PREDICATES VARCHAR2(4000),
  PROJECTION        VARCHAR2(4000),
  TIME              NUMBER,
  QBLOCK_NAME       VARCHAR2(30)
);

-- and then use the foliwng query to save in the plan

EXPLAIN PLAN INTO my_plan_table FOR SELECT * FROM LINEORDER;

partition by range (LO_OrderDateKey)  (
partition p1992_01 values less than (19920131),
partition p1992_02 values less than (19920231),
partition p1992_03 values less than (19920331),
partition p1992_04 values less than (19920431),
partition p1992_05 values less than (19920531),
partition p1992_06 values less than (19920631),
partition p1992_07 values less than (19920731),
partition p1992_08 values less than (19920831),
partition p1992_09 values less than (19920931),
partition p1992_10 values less than (19921031),
partition p1992_11 values less than (19921131),
partition p1992_12 values less than (19921231),
partition p1993_01 values less than (19930131),
partition p1993_02 values less than (19930231),
partition p1993_03 values less than (19930331),
partition p1993_04 values less than (19930431),
partition p1993_05 values less than (19930531),
partition p1993_06 values less than (19930631),
partition p1993_07 values less than (19930731),
partition p1993_08 values less than (19930831),
partition p1993_09 values less than (19930931),
partition p1993_10 values less than (19931031),
partition p1993_11 values less than (19931131),
partition p1993_12 values less than (19931231),
partition p1994_01 values less than (19940131),
partition p1994_02 values less than (19940231),
partition p1994_03 values less than (19940331),
partition p1994_04 values less than (19940431),
partition p1994_05 values less than (19940531),
partition p1994_06 values less than (19940631),
partition p1994_07 values less than (19940731),
partition p1994_08 values less than (19940831),
partition p1994_09 values less than (19940931),
partition p1994_10 values less than (19941031),
partition p1994_11 values less than (19941131),
partition p1994_12 values less than (19941231),
partition p1995_01 values less than (19950131),
partition p1995_02 values less than (19950231),
partition p1995_03 values less than (19950331),
partition p1995_04 values less than (19950431),
partition p1995_05 values less than (19950531),
partition p1995_06 values less than (19950631),
partition p1995_07 values less than (19950731),
partition p1995_08 values less than (19950831),
partition p1995_09 values less than (19950931),
partition p1995_10 values less than (19951031),
partition p1995_11 values less than (19951131),
partition p1995_12 values less than (19951231),
partition p1996_01 values less than (19960131),
partition p1996_02 values less than (19960231),
partition p1996_03 values less than (19960331),
partition p1996_04 values less than (19960431),
partition p1996_05 values less than (19960531),
partition p1996_06 values less than (19960631),
partition p1996_07 values less than (19960731),
partition p1996_08 values less than (19960831),
partition p1996_09 values less than (19960931),
partition p1996_10 values less than (19961031),
partition p1996_11 values less than (19961131),
partition p1996_12 values less than (19961231),
partition p1997_01 values less than (19970131),
partition p1997_02 values less than (19970231),
partition p1997_03 values less than (19970331),
partition p1997_04 values less than (19970431),
partition p1997_05 values less than (19970531),
partition p1997_06 values less than (19970631),
partition p1997_07 values less than (19970731),
partition p1997_08 values less than (19970831),
partition p1997_09 values less than (19970931),
partition p1997_10 values less than (19971031),
partition p1997_11 values less than (19971131),
partition p1997_12 values less than (19971231),
partition p1998_01 values less than (19980131),
partition p1998_02 values less than (19980231),
partition p1998_03 values less than (19980331),
partition p1998_04 values less than (19980431),
partition p1998_05 values less than (19980531),
partition p1998_06 values less than (19980631),
partition p1998_07 values less than (19980731),
partition p1998_08 values less than (19980831),
partition p1998_09 values less than (19980931),
partition p1998_10 values less than (19981031),
partition p1998_11 values less than (19981131),
partition p1998_12 values less than (19981231)
) ;

PARTITION BY HASH (LO_OrderDateKey) PARTITIONS 8;


-- the test queries after fragmentation
select 
    d_year, s_nation, p_category,
    sum(lo_revenue - lo_supplycost) as profit
from 
    DATES, CUSTOMER, SUPPLIER, PART, LINEORDER
where 
    lo_custkey = c_custkey
    and lo_suppkey = s_suppkey
    and lo_partkey = p_partkey
    and lo_orderdate = d_datekey
    and c_region = 'AMERICA'
    and s_region = 'AMERICA'
    and (d_year = 1997 or d_year = 1998)
    and (p_mfgr = 'MFGR#1' or p_mfgr = 'MFGR#2')
group by 
    d_year, s_nation, p_category
order by 
    d_year, s_nation, p_category;

select 
d_year, c_nation,
sum(lo_revenue - lo_supplycost) as profit
from 
    DATES, CUSTOMER, SUPPLIER, PART, LINEORDER
where 
    lo_custkey = c_custkey
    and lo_suppkey = s_suppkey
    and lo_partkey = p_partkey
    and lo_orderdate = d_datekey
    and c_region = 'AMERICA'
    and s_region = 'AMERICA'
    and (p_mfgr = 'MFGR#1' or p_mfgr = 'MFGR#2')
group by 
    d_year, c_nation
order by 
    d_year, c_nation;