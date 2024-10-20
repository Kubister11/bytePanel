<template>
  <div class="w-4/6 h-full rounded-[10px] bg-[#161827] bg-opacity-80 flex flex-col">
    <div class="w-full h-[60px] bg-black bg-opacity-15 rounded-t-[10px] flex items-center">
      <div class="flex gap-5 ml-5 mr-5 items-center w-full">
        <font-awesome-icon :icon="['fas', 'server']" class="text-white size-6" />
        <div class="w-[1px] h-[28px] bg-white"></div>
        <p class="text-[#ADC4FF] bold-font text-[20px]">BOXPVP_1</p>
      </div>
      <div class="flex gap-2 ml-5 mr-5 items-center justify-center w-full">
        <p class="text-white opacity-15 bold-font text-[16px] tracking-widest">N1</p>
        <div class="w-2 h-2 bg-white opacity-25 rounded-full"></div>
        <p class="text-white opacity-15 bold-font text-[16px] tracking-widest">0.0.0.0:25565</p>
      </div>
      <div class="flex gap-3 ml-5 mr-5 items-center justify-end w-full">
        <p class="text-[#B9FFA4] text-[15px]">WebSocket Online</p>
        <div class="w-4 h-4 bg-[#33FF00] rounded-full shadow-[0_0_10px_1px_#33FF00]"></div>
      </div>
    </div>
    <div class="w-full h-full">
<!--      <p class="text-white font-[Consolas]">-->
<!--        Starting org.bukkit.craftbukkit.Main-->
<!--        System Info: Java 22 (OpenJDK 64-Bit Server VM 22.0.2+9) Host: Linux 6.1.0-26-amd64 (amd64)-->
<!--        Loading libraries, please wait...-->
<!--        [21:28:29 INFO]: Environment: Environment[sessionHost=https://sessionserver.mojang.com, servicesHost=https://api.minecraftservices.com, name=PROD]-->
<!--        [21:28:29 INFO]: Loaded 1174 recipes-->
<!--        [21:28:29 INFO]: Loaded 0 advancements-->
<!--        [21:28:30 INFO]: Starting minecraft server version 1.20.4-->
<!--        [21:28:30 INFO]: Loading properties-->
<!--        [21:28:30 INFO]: This server is running SSSpigot version SSSpigot2-1.20.4-20240220-4b77e05 (MC: 1.20.4) (Implementing API version 1.20.4-R0.1-SNAPSHOT) (Git: 4b77e05 on 1.20.4)-->
<!--        [21:28:30 INFO]: Server Ping Player Sample Count: 12-->
<!--        [21:28:30 INFO]: Using 6 threads for Netty based IO-->
<!--        [21:28:30 INFO]: [ChunkTaskScheduler] Chunk system is using 1 I/O threads, 8 worker threads, and gen parallelism of 8 threads-->
<!--        [21:28:30 WARN]: [Pufferfish] Will not enable SIMD! These optimizations are only safely supported on Java 17, Java 18, and Java 19.-->
<!--        [21:28:30 INFO]: Default game type: SURVIVAL-->
<!--        [21:28:30 INFO]: Generating keypair-->
<!--        [21:28:30 INFO]: Starting Minecraft server on 0.0.0.0:3001-->
<!--        [21:28:30 INFO]: Using epoll channel type-->
<!--        [21:28:30 INFO]: Paper: Using libdeflate (Linux x86_64) compression from Velocity.-->
<!--        [21:28:30 INFO]: Paper: Using OpenSSL 3.0.x (Linux x86_64) cipher from Velocity.-->
<!--        [21:28:30 INFO]: [SpigotLibraryLoader] [CORE - System] Loading 9 libraries... please wait-->
<!--        [21:28:31 INFO]: [SpigotLibraryLoader] [CORE - System] Loaded library /home/container/libraries/org/redisson/redisson/3.36.0/redisson-3.36.0.jar-->
<!--        [21:28:31 INFO]: [SpigotLibraryLoader] [CORE - System] Loaded library /home/container/libraries/io/netty/netty-common/4.1.112.Final/netty-common-4.1.112.Final.jar-->
<!--        [21:28:31 INFO]: [SpigotLibraryLoader] [CORE - System] Loaded library /home/container/libraries/io/netty/netty-codec/4.1.112.Final/netty-codec-4.1.112.Final.jar-->
<!--        [21:28:31 INFO]: [SpigotLibraryLoader] [CORE - System] Loaded library /home/container/libraries/io/netty/netty-buffer/4.1.112.Final/netty-buffer-4.1.112.Final.jar-->
<!--        [21:28:31 INFO]: [SpigotLibraryLoader] [CORE - System] Loaded library /home/container/libraries/io/netty/netty-transport/4.1.112.Final/netty-transport-4.1.112.Final.jar-->
<!--        [21:28:31 INFO]: [SpigotLibraryLoader] [CORE - System] Loaded library /home/container/libraries/io/netty/netty-resolver/4.1.112.Final/netty-resolver-4.1.112.Final.jar-->
<!--        [21:28:31 INFO]: [SpigotLibraryLoader] [CORE - System] Loaded library /home/container/libraries/io/netty/netty-resolver-dns/4.1.112.Final/netty-resolver-dns-4.1.112.Final.jar-->
<!--        [21:28:31 INFO]: [SpigotLibraryLoader] [CORE - System] Loaded library /home/container/libraries/io/netty/netty-codec-dns/4.1.112.Final/netty-codec-dns-4.1.112.Final.jar-->
<!--      </p>-->
    </div>
    <div class="w-full h-[80px] bg-black bg-opacity-15 rounded-b-[10px] flex items-center justify-center">
      <div class="w-full flex justify-center gap-2">
        <input type="text" placeholder="Type command..." class="w-[91%] h-[40px] text-white p-4 rounded-l-[10px] bg-[#B1CDFF] bg-opacity-20 placeholder">
        <button @click="toast.success('Command sent!', { timeout: 2000 })" class="rounded-r-[10px] bg-[#009DFF] bg-opacity-20 w-[4%] flex justify-center items-center group">
          <font-awesome-icon :icon="['fas', 'paper-plane']" class="text-white size-5 opacity-50 group-hover:opacity-100 duration-100" />
        </button>
      </div>
    </div>
  </div>
</template>
<script setup lang="js">

import { useToast } from "vue-toastification";
const toast = useToast();

</script>