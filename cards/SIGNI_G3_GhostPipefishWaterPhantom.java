package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G3_GhostPipefishWaterPhantom extends Card {
    
    public SIGNI_G3_GhostPipefishWaterPhantom()
    {
        setImageSets("WXDi-P03-079");
        
        setOriginalName("幻水　カミソリウオ");
        setAltNames("ゲンスイカミソリウオ Gensui Kamisoriuo");
        setDescription("jp",
                "@C：あなたの手札が４枚以上あるかぎり、このシグニのパワーは＋3000される。\n" +
                "@E %G：カードを１枚引く。" +
                "~#：カードを１枚引き[[エナチャージ２]]をする。"
        );
        
        setName("en", "Solenostomus, Phantom Aquatic Beast");
        setDescription("en",
                "@C: As long as you have four or more cards in your hand, this SIGNI gets +3000 power.\n" +
                "@E %G: Draw a card." +
                "~#Draw a card and [[Ener Charge 2]]."
        );
        
        setName("en_fan", "Ghost Pipefish, Water Phantom");
        setDescription("en_fan",
                "@C: As long as there are 4 or more cards in your hand, this SIGNI gets +3000 power.\n" +
                "@E %G: Draw 1 card." +
                "~#Draw 1 card and [[Ener Charge 2]]."
        );
        
		setName("zh_simplified", "幻水 剃刀鱼");
        setDescription("zh_simplified", 
                "@C :你的手牌在4张以上时，这只精灵的力量+3000。\n" +
                "@E %G:抽1张牌。" +
                "~#抽1张牌并[[能量填充2]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
            enerCharge(2);
        }
    }
}
