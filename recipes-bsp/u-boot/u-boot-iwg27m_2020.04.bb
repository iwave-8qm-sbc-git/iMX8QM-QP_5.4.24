# Copyright (C) 2020 iWave Systems Technologies Pvt Ltd.

DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."
require u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
inherit pythonnative

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PROVIDES += "u-boot"
DEPENDS_append = " dtc-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

UBOOT_SRC ?= "git://github.com/iwave-8qm-sbc-git/u-boot-iwg27m.git;protocol=https"
SRCBRANCH = "IMX8QM/QP-R4.0-REL1.0"
SRC_URI = "${UBOOT_SRC};branch=${SRCBRANCH} \
	${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'file://PATCH001-iW-PRFHZ-SC-01-R4.0-REL1.0-Linux5.4.24_Uboot_4GB_LPDDR4_Optee.patch', "", d)}"
SRCREV = "e19de9e1b0c14bcc0d6e8439a05f0c2b2e441187"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-5.4.24-2.1.0"

BOOT_TOOLS = "imx-boot-tools"

do_deploy_append_mx8m () {
    # Deploy u-boot-nodtb.bin and fsl-imx8mq-XX.dtb, to be packaged in boot binary by imx-boot
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME}  ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${UBOOT_CONFIG}
                fi
            done
            unset  j
        done
        unset  i
    fi

}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

UBOOT_NAME_mx6 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME_mx7 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME_mx8 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
