package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_K2_ReaperPhantomApparition extends Card {

    public SIGNI_K2_ReaperPhantomApparition()
    {
        setImageSets("WX25-P1-105");

        setOriginalName("幻怪　リーパー");
        setAltNames("ゲンカイリーパー Genkai Riipaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたがアーツを使用していた場合、あなたのエナゾーンから＜怪異＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、このシグニは[[アサシン（パワー5000以下のシグニ）]]を得る。"
        );

        setName("en", "Reaper, Phantom Apparition");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you used ARTS this turn, you may put 1 <<Apparition>> SIGNI from your ener zone into the trash. If you do, until end of turn, this SIGNI gains [[Assassin (SIGNI with power 5000 or less)]]."
        );

		setName("zh_simplified", "幻怪 收割者");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你把必杀使用过的场合，可以从你的能量区把<<怪異>>精灵1张放置到废弃区。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀（力量5000以下的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.APPARITION).fromEner()).get();
                
                if(trash(cardIndex))
                {
                    attachAbility(getCardIndex(), new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
                }
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
