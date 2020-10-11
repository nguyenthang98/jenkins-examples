NODE_COUNT = 2
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"
  (1..NODE_COUNT).each do |i|
    config.vm.define "jenkin-node-#{i}" do |node|
      node.vm.network "private_network", ip: "192.168.100.2#{i}"
      node.vm.hostname = "jenkin-node-#{i}"
      node.vm.provider :virtualbox do |vb, override|
        vb.memory = "1024"
        vb.cpus = "2"
      end
    end
  end
  config.vm.provision "shell", path: "scripts/docker.sh"
	config.vm.provision "shell", path: "scripts/docker-compose.sh"
  config.vm.provision "shell", inline: <<-SHELL
     # enable ntp
     sudo timedatectl set-timezone Asia/Ho_Chi_Minh
     sudo timedatectl set-ntp true
     # enable ssh password authentication
     sed -i 's/PasswordAuthentication no/PasswordAuthentication yes/g' /etc/ssh/sshd_config    
     systemctl restart sshd.service
  SHELL
end
