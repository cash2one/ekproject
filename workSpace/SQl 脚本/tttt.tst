PL/SQL Developer Test script 3.0
10
declare v_job number:=1;

begin

dbms_job.submit(v_job,'PRO_STAT_SCHOOL_USE_EVERYDAY;',sysdate,'TRUNC(SYSDATE + 1)');

commit;

end;

0
0
