package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G2_SnappingTurtleWaterPhantom extends Card {
    
    public SIGNI_G2_SnappingTurtleWaterPhantom()
    {
        setImageSets("WXDi-P06-076");
        
        setOriginalName("幻水　カミツキガメ");
        setAltNames("ゲンスイカミツキガメ Gensui Kamitsukigame");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの手札からカード１枚をエナゾーンに置いてもよい。\n" +
                "@E %X %X %X：カードを２枚引く。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Chelydridae, Phantom Aquatic Beast");
        setDescription("en",
                "@U: At the end of your turn, you may put a card from your hand into your Ener Zone.\n" +
                "@E %X %X %X: Draw two cards." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Snapping Turtle, Water Phantom");
        setDescription("en_fan",
                "@U: At the end of your turn, you may put 1 card from your hand into the ener zone.\n" +
                "@E %X %X %X: Draw 2 cards." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );
        
		setName("zh_simplified", "幻水 拟鳄龟");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，可以从你的手牌把1张牌放置到能量区。\n" +
                "@E %X %X %X:抽2张牌。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().fromHand()).get();
            putInEner(cardIndex);
        }
        
        private void onEnterEff()
        {
            draw(2);
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
