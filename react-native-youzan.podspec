require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-youzan"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-youzan
                   DESC
  s.homepage     = "https://github.com/ybwdaisy/react-native-youzan"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "ybwdaisy" => "ybw_daisy@163.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/ybwdaisy/react-native-youzan.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "YZAppSDK"
end

