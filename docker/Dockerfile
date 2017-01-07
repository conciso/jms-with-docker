From jboss/wildfly:10.1.0.Final

EXPOSE 8080 9990

RUN /opt/jboss/wildfly/bin/add-user.sh jboss jboss --silent
RUN /opt/jboss/wildfly/bin/add-user.sh -a jboss jboss -g guest --silent

CMD /opt/jboss/wildfly/bin/standalone.sh -c standalone-full.xml -b=$(hostname -i) -bmanagement=0.0.0.0
