library(ggplot2)

producerAO = read.csv("acProd0.csv")
consumerAO = read.csv("acCons0.csv")

producerAsync = read.csv("AsyncProd0.csv")
consumerAsync = read.csv("AsyncCons0.csv")

#Time SleepTime
avg_producerAOTimeSleepTime= aggregate( Time ~SleepTime, data=producerAO, FUN=mean)
avg_consumerAOTimeSleepTime= aggregate( Time ~SleepTime, data=consumerAO, FUN=mean)
avg_producerAsyncTimeSleepTime= aggregate( Time ~SleepTime, data=producerAsync, FUN=mean)
avg_consumerAsyncTimeSleepTime= aggregate( Time ~SleepTime, data=consumerAsync, FUN=mean)


fit_ProdAO = lm(Time ~ poly(SleepTime, 4, raw = TRUE), data=avg_producerAOTimeSleepTime)
newdata_ProdAO= data.frame(SleepTime = seq(0,5, 1))
newdata_ProdAO$Time = predict(fit_ProdAO, newdata_ProdAO)

fit_ProdAS = lm(Time ~ poly(SleepTime, 4, raw = TRUE), data=avg_producerAsyncTimeSleepTime)
newdata_ProdAS= data.frame(SleepTime = seq(0,5, 1))
newdata_ProdAS$Time = predict(fit_ProdAS, newdata_ProdAS)

plotProd = ggplot(newdata_ProdAO, aes(x=SleepTime, y=Time),  colour="black" )+
  geom_line()
plotProd
plotProd2 = plotProd   + 
  geom_line(data=newdata_ProdAS, aes(SleepTime, Time), colour="green") + 
  xlab("SleepTime")+ ylab("Time[ms]")
plotProd2

fit_ConsAO = lm(Time ~ poly(SleepTime, 4, raw = TRUE), data=avg_consumerAOTimeSleepTime)
newdata_ConsAO= data.frame(SleepTime = seq(0,5, 1))
newdata_ConsAO$Time = predict(fit_ConsAO, newdata_ConsAO)

fit_ConsAS = lm(Time ~ poly(SleepTime, 4, raw = TRUE), data=avg_consumerAsyncTimeSleepTime)
newdata_ConsAS= data.frame(SleepTime =  seq(0,5, 1))
newdata_ConsAS$Time = predict(fit_ConsAS, newdata_ConsAS)

plotCons = ggplot(newdata_ConsAO, aes(x=SleepTime, y=Time),  colour="black" )+
  geom_line()
plotCons
plotCons2 = plotCons   + 
  geom_line(data=newdata_ConsAS, aes(SleepTime, Time), colour="green")+
  xlab("SleepTime")+ ylab("Time[ms]")
plotCons2


# Time Threads
avg_producerAOTimeThreads= aggregate( Time ~Threads, data=producerAO, FUN=mean)
avg_consumerAOTimeThreads= aggregate( Time ~Threads, data=consumerAO, FUN=mean)
avg_producerAsyncTimeThreads= aggregate( Time ~Threads, data=producerAsync, FUN=mean)
avg_consumerAsyncTimeThreads= aggregate( Time ~Threads, data=consumerAsync, FUN=mean)

fit_ProdAO = lm(Time ~ poly(Threads, 4, raw = TRUE), data=avg_producerAOTimeThreads)
newdata_ProdAO= data.frame(Threads = seq(10,100, 10))
newdata_ProdAO$Time = predict(fit_ProdAO, newdata_ProdAO)

fit_ProdAS = lm(Time ~ poly(Threads, 4, raw = TRUE), data=avg_producerAsyncTimeThreads)
newdata_ProdAS= data.frame(Threads = seq(10,100, 10))
newdata_ProdAS$Time = predict(fit_ProdAS, newdata_ProdAS)

plotProd = ggplot(newdata_ProdAO, aes(x=Threads, y=Time),  colour="black" )+
  geom_line()
plotProd
plotProd2 = plotProd   + 
  geom_line(data=newdata_ProdAS, aes(Threads, Time), colour="green") + 
  xlab("Threads")+ ylab("Time[ms]")
plotProd2

fit_ConsAO = lm(Time ~ poly(Threads, 4, raw = TRUE), data=avg_consumerAOTimeThreads)
newdata_ConsAO= data.frame(Threads = seq(10,100, 10))
newdata_ConsAO$Time = predict(fit_ConsAO, newdata_ConsAO)

fit_ConsAS = lm(Time ~ poly(Threads, 4, raw = TRUE), data=avg_consumerAsyncTimeThreads)
newdata_ConsAS= data.frame(Threads = seq(10,100, 10))
newdata_ConsAS$Time = predict(fit_ConsAS, newdata_ConsAS)

plotCons = ggplot(newdata_ConsAO, aes(x=Threads, y=Time),  colour="black" )+
  geom_line()
plotCons2 = plotCons   + 
  geom_line(data=newdata_ConsAS, aes(Threads, Time), colour="green")+
  xlab("Threads")+ ylab("Time[ms]")
plotCons2

