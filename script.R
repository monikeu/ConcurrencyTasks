library(ggplot2)

producerActiveObject = read.csv("acProd.csv")
consumerActiveObject = read.csv("acCons.csv")

producerN = read.csv("NProd.csv")
consumerN = read.csv("NCons.csv")

avg_producerActiveObject= aggregate( Time ~Amount, data=producerActiveObject, FUN=mean)
avg_consumerActiveObject= aggregate( Time ~Amount, data=consumerActiveObject, FUN=mean)
avg_producerN= aggregate( Time ~Amount, data=producerN, FUN=mean)
avg_consumerN= aggregate( Time ~Amount, data=consumerN, FUN=mean)

plotProds = ggplot(avg_producerActiveObject, aes(x=Amount, y=Time),  colour="black") + geom_line() +  xlab("Amount")+ ylab("Time[ms]")
plotProds
plotProds2 = plotProds +  geom_line(data=avg_producerN, aes(x=Amount, y=Time), colour="blue")
plotProds2

plotCons = ggplot(avg_consumerActiveObject, aes(x=Amount, y=Time),  colour="black") + geom_line() +  xlab("Amount")+ ylab("Time[ms]")
plotCons
plotCons2 = plotCons + geom_line(data=avg_consumerN, colour="blue") + geom_line()
plotCons2

producerActiveObject = read.csv("acProd1000.csv")
consumerActiveObject = read.csv("acCons1000.csv")

producerN = read.csv("NProd1000.csv")
consumerN = read.csv("NCons1000.csv")

avg_producerActiveObject= aggregate( Time ~Amount, data=producerActiveObject, FUN=mean)
avg_consumerActiveObject= aggregate( Time ~Amount, data=consumerActiveObject, FUN=mean)
avg_producerN= aggregate( Time ~Amount, data=producerN, FUN=mean)
avg_consumerN= aggregate( Time ~Amount, data=consumerN, FUN=mean)

plotProds = ggplot(avg_producerActiveObject, aes(x=Amount, y=Time),  colour="black") + geom_line() +  xlab("Amount")+ ylab("Time[ms]")
plotProds
plotProds2 = plotProds +  geom_line(data=avg_producerN, aes(x=Amount, y=Time), colour="blue")
plotProds2

plotCons = ggplot(avg_consumerActiveObject, aes(x=Amount, y=Time),  colour="black") + geom_line() +  xlab("Amount")+ ylab("Time[ms]")
plotCons
plotCons2 = plotCons + geom_line(data=avg_consumerN, colour="blue") + geom_line()
plotCons2


