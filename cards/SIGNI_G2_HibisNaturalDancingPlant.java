package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G2_HibisNaturalDancingPlant extends Card {
    
    public SIGNI_G2_HibisNaturalDancingPlant()
    {
        setImageSets("WXDi-P08-071", "SPDi38-18");
        
        setOriginalName("羅踊植　ハイビス");
        setAltNames("ラヨウショクハイビス Rayoushoku Haibisu");
        setDescription("jp",
                "@C：このシグニのパワーはあなたのエナゾーンにあるカードが持つ色の種類１つにつき＋1000される。\n" +
                "@C：対戦相手のターンの間、あなたのエナゾーンにあるカードが持つ色が合計３種類以上あるかぎり、このシグニは[[シャドウ（レベル２以下のシグニ）]]を得る。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Hibiscus, Natural Dancing Plant");
        setDescription("en",
                "@C: This SIGNI gets +1000 power for each different color among cards in your Ener Zone. \n" +
                "@C: During your opponent's turn, as long as there are three or more different colors among cards in your Ener Zone, this SIGNI gains [[Shadow -- Level two or less SIGNI]]." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Hibis, Natural Dancing Plant");
        setDescription("en_fan",
                "@C: This SIGNI gets +1000 power for each color among cards in your ener zone.\n" +
                "@C: During your opponent's turn, while there are 3 or more colors among cards in your ener zone, this SIGNI gains [[Shadow (level 2 SIGNI)]]." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );
        
		setName("zh_simplified", "罗踊植 木槿");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量依据你的能量区的牌持有颜色的种类的数量，每有1种就+1000。（无色不含有颜色）\n" +
                "@C $TP :你的能量区的牌持有颜色在合计3种类以上时，这只精灵得到[[暗影（等级2以下的精灵）]]。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PowerModifier(this::onConstEff1ModGetValue));
            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEff2ModGetSample));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private double onConstEff1ModGetValue(CardIndex cardIndex)
        {
            return 1000 * CardAbilities.getColorsCount(getCardsInEner(getOwner()));
        }
        
        private ConditionState onConstEff2Cond()
        {
            return !isOwnTurn() && CardAbilities.getColorsCount(getCardsInEner(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() == 2 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
