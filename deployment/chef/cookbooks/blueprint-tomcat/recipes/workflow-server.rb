=begin
#<
This recipe installs and configures the CoreMedia Blueprint Workflow Server.
#>
=end

service_name = 'workflow-server'
node.default['blueprint']['webapps'][service_name]['application.properties']['cap.client.server.ior.url'] = "#{cm_webapp_url('content-management-server')}/ior"
node.default['blueprint']['webapps'][service_name]['application.properties']['workflow.server.ORBServerHost'] = node['blueprint']['hostname']

blueprint_tomcat_service service_name
