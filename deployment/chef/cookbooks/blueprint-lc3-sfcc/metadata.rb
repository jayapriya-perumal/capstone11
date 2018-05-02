name 'blueprint-lc3-sfcc'
maintainer 'The Authors'
maintainer_email 'you@example.com'
license 'all_rights'
description 'Installs/Configures CoreMedia LiveContext for Salesforce Commerce Cloud'
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version '1.0.0'

depends 'blueprint-base'
depends 'blueprint-tomcat'
depends 'blueprint-proxy'
depends 'coremedia-proxy', '~> 1.0.0'
