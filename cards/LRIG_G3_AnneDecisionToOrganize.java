package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityMultiEner;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G3_AnneDecisionToOrganize extends Card {
    
    public LRIG_G3_AnneDecisionToOrganize()
    {
        setImageSets("WXDi-P08-009", "WXDi-P08-009U", Mask.IGNORE+"WXDi-P181P");
        
        setOriginalName("結団の決断　アン＝サード");
        setAltNames("ケツダンのケツダンアンサード Ketsudan no Ketsudan An Saado");
        setDescription("jp",
                "@E：あなたのエナゾーンからシグニを２枚まで対象とし、それらを手札に加える。\n" +
                "@A $T1 %G0：あなたのエナゾーンにあるカード１枚を対象とし、ターン終了時まで、それは【マルチエナ】を得る。\n" +
                "@A $G1 %G0：対戦相手のシグニのうち、最も高いパワーを持つすべてのシグニを手札に戻す。"
        );
        
        setName("en", "Ann III, Maiden of Mettle");
        setDescription("en",
                "@E: Add up to two target SIGNI from your Ener Zone to your hand.\n" +
                "@A $T1 %G0: Target card in your Ener Zone gains [[Multi Ener]] until end of turn.\n" +
                "@A $G1 %G0: Return all SIGNI with the highest power on your opponent's field to their owner's hand."
        );
        
        setName("en_fan", "Anne-Third, Decision to Organize");
        setDescription("en_fan",
                "@E: Target up to 2 SIGNI from your ener zone, and add them to your hand.\n" +
                "@A $T1 %G0: Target 1 card in your ener zone, and until end of turn, it gains [[Multi Ener]].\n" +
                "@A $G1 %G0: Return all of your opponent's SIGNI with the highest power to their hand."
        );
        
		setName("zh_simplified", "团结的决断 安=THIRD");
        setDescription("zh_simplified", 
                "@E :从你的能量区把精灵2张最多作为对象，将这些加入手牌。\n" +
                "@A $T1 %G0:你的能量区的1张牌作为对象，直到回合结束时为止，其得到[[万花色]]。\n" +
                "@A $G1 %G0:对战对手的精灵中，持有最高力量的全部的精灵返回手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner());
            addToHand(data);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().fromEner()).get();
            if(target != null) attachAbility(target, new StockAbilityMultiEner(), ChronoDuration.turnEnd());
        }
        
        private void onActionEff2()
        {
            DataTable<CardIndex> data = getSIGNIOnField(getOpponent());
            double max = data.stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).max().orElse(0);
            
            DataTable<CardIndex> dataToHand = new DataTable<>();
            data.stream().filter(c -> c.getIndexedInstance().getPower().getValue() == max).forEachOrdered(i -> dataToHand.add(i));
            addToHand(dataToHand);
        }
    }
}
