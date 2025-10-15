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

public final class SIGNI_K3_MetsumeWickedDevilPrincess extends Card {

    public SIGNI_K3_MetsumeWickedDevilPrincess()
    {
        setImageSets("WX24-D5-20");

        setOriginalName("凶魔姫　メツメ");
        setAltNames("キョウマキメツメ Kyoumaki Metsume");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを３枚トラッシュに置く。&E５枚以上@0代わりに対戦相手のデッキの上からカードを５枚トラッシュに置く。" +
                "~#どちらか１つを選ぶ。\n$$1対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。\n$$2カードを１枚引く。"
        );

        setName("en", "Metsume, Wicked Devil Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, put the top 3 cards of your opponent's deck into the trash. &E5 or more@0 Instead, put the top 5 cards of your opponent's deck into the trash." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "凶魔姬 缅茨缅");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，从对战对手的牌组上面把3张牌放置到废弃区。&E5张以上@0作为替代，从对战对手的牌组上面把5张牌放置到废弃区。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);

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
            auto.setRecollect(5);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            millDeck(getOpponent(), !getAbility().isRecollectFulfilled() ? 3 : 5);
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
                gainPower(target, -15000, ChronoDuration.turnEnd());
            } else {
                draw(1);
            }
        }
    }
}
