package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K2_HarunaKurodateNewYears extends Card {

    public SIGNI_K2_HarunaKurodateNewYears()
    {
        setImageSets("WX25-CP1-092");

        setOriginalName("黒舘ハルナ(正月)");
        setAltNames("クロダテハルナショウガツ Kurodate Haruna Shougatsu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニを３体まで対象とし、それらのシグニ１体につきあなたのエナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、それらのパワーを－3000する。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを２枚トラッシュに置く。"
        );

        setName("en", "Kurodate Haruna (New Year's)");

        setName("en_fan", "Haruna Kurodate (New Year's)");
        setDescription("en",
                "@U: At the beginning of your attack phase, target up to 3 of your opponent's SIGNI, and you may put 1 <<Blue Archive>> card for each of them from your ener zone into the trash. If you do, until end of turn, they get --3000 power." +
                "~{{U: At the beginning of your attack phase, put the top 2 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "黑馆晴奈(正月)");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵3只最多作为对象，可以依据这些精灵的数量，每有1只就从你的能量区把<<ブルアカ>>牌1张放置到废弃区。这样做的场合，直到回合结束时为止，这些的力量-3000。\n" +
                "~{{U:你的攻击阶段开始时，从对战对手的牌组上面把2张牌放置到废弃区。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.MINUS).OP().SIGNI());
            
            if(data.get() != null)
            {
                DataTable<CardIndex> dataFromEner = playerTargetCard(0,data.size(), ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
                
                if(trash(dataFromEner) > 0)
                {
                    gainPower(data, -3000, ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onAutoEff2(CardIndex caller)
        {
            millDeck(getOpponent(), 2);
        }
    }
}
