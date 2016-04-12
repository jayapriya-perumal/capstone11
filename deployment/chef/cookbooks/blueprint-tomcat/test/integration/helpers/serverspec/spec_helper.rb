require 'serverspec'
require 'yarjuf'

set :backend, :exec

RSpec.configure do |c|
  FileUtils.mkdir_p '/shared'
  c.output_stream = File.open('/shared/serverspec-reports.xml', 'w')
  c.formatter = 'JUnit'
end
