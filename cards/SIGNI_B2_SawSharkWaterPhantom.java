package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_SawSharkWaterPhantom extends Card {

    public SIGNI_B2_SawSharkWaterPhantom()
    {
        setImageSets("WX24-P3-076");

        setOriginalName("幻水　ノコギリザメ");
        setAltNames("ゲンスイノコギリザメ Gensui Nokogirizame");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、あなたのエナゾーンから＜水獣＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、それのパワーを－3000する。あなたの手札が５枚以上ある場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Saw Shark, Water Phantom");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may put 1 <<Water Beast>> SIGNI from your ener zone into the trash. If you do, until end of turn, it gets --3000 power. If there are 5 or more cards in your hand, until end of turn, it gets --5000 power instead."
        );

		setName("zh_simplified", "幻水 锯鲨");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从你的能量区把<<水獣>>精灵1张放置到废弃区。这样做的场合，直到回合结束时为止，其的力量-3000。你的手牌在5张以上的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.WATER_BEAST).fromEner()).get();
                
                if(cardIndex != null)
                {
                    trash(cardIndex);
                    
                    gainPower(target, getHandCount(getOwner()) < 5 ? -3000 : -5000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
