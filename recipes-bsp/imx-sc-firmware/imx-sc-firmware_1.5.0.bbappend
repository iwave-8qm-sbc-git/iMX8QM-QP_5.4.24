# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2019 NXP

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_imx8qm-iwg27m = " file://mx8qm-iwg27m-scfw-tcm_4gb.bin"
SRC_URI_append_imx8qm-iwg27m-8gb = " file://mx8qm-iwg27m-scfw-tcm_8gb.bin"

SC_FIRMWARE_NAME ?= "INVALID"
SC_FIRMWARE_NAME_imx8qm-iwg27m = "mx8qm-iwg27m-scfw-tcm_4gb.bin"
SC_FIRMWARE_NAME_imx8qm-iwg27m-8gb = "mx8qm-iwg27m-scfw-tcm_8gb.bin"

# iWave SCFW are in the workdir not in the git repository move them
do_deploy_prepend() {
	mv ${WORKDIR}/${SC_FIRMWARE_NAME} ${S}/
}
