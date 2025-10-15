package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K1_IzumiShishidou extends Card {

    public SIGNI_K1_IzumiShishidou()
    {
        setImageSets("WXDi-CP02-093");

        setOriginalName("獅子堂イズミ");
        setAltNames("シシドウイズミ Shishidou Izumi");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にある＜ブルアカ＞のシグニ１体につき対戦相手のデッキの上からカードを１枚トラッシュに置く。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを２枚トラッシュに置く。"
        );

        setName("en", "Shishidou Izumi");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, put the top card of your opponent's deck into their trash for each <<Blue Archive>> SIGNI on your field.~{{U: At the beginning of your attack phase, put the top two cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Izumi Shishidou");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, put the top card of your opponent's deck into the trash for each of your <<Blue Archive>> SIGNI on the field." +
                "~{{U: At the beginning of your attack phase, put the top 2 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "狮子堂泉");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，依据你的场上的<<ブルアカ>>精灵的数量，每有1只就从对战对手的牌组上面把1张牌放置到废弃区。\n" +
                "~{{U:你的攻击阶段开始时，从对战对手的牌组上面把2张牌放置到废弃区。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onAutoEff1()
        {
            millDeck(getOpponent(), new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getValidTargetsCount());
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            millDeck(getOpponent(), 2);
        }
    }
}
