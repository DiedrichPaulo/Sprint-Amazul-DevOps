set -e

RESOURCE_GROUP="rg-nuclearquest"
LOCATION="brazilsouth"
VM_NAME="vm-nuclearquest"
VM_SIZE="Standard_D2s_v4"
ADMIN_USER="azureuser"

echo "Criando Resource Group..."

az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION"

echo "Criando máquina virtual..."

az vm create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$VM_NAME" \
  --location "$LOCATION" \
  --image Ubuntu2404 \
  --size "$VM_SIZE" \
  --admin-username "$ADMIN_USER" \
  --generate-ssh-keys \
  --public-ip-sku Standard \
  --storage-sku Standard_LRS \
  --nsg-rule SSH

echo "Liberando porta 8080..."

az vm open-port \
  --resource-group "$RESOURCE_GROUP" \
  --name "$VM_NAME" \
  --port 8080

echo "Provisionamento concluído!"

echo "Endereço IP da VM:"

az vm list-ip-addresses \
  --resource-group "$RESOURCE_GROUP" \
  --name "$VM_NAME" \
  --output table
