import datetime
import time


class Snow:
    """雪花算法生成全局自增唯一id"""

    init_date = time.strptime('2022-09-28 00:00:00', "%Y-%m-%d %H:%M:%S")
    start = int(time.mktime(init_date) * 1000)
    last = int(time.time() * 1000)
    pc_room = 1
    pc = 1
    seq = 0

    @classmethod
    def get_guid(cls):
        """获取雪花算法生成的id"""
        now = int(time.time() * 1000)
        if now != cls.last:
            cls.last = now
            cls.seq = 1
        else:
            while cls.seq >= 4096:
                time.sleep(0.1)
                return cls.get_guid()
            cls.seq += 1

        time_diff = now - cls.start
        pk = (time_diff << 22) ^ (cls.pc_room << 18) ^ (cls.pc << 12) ^ cls.seq

        return pk

if __name__ == '__main__':
    print(datetime.datetime.now())