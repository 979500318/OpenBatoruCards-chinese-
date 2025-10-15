package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_MidoriSaiba extends Card {

    public SIGNI_B2_MidoriSaiba()
    {
        setImageSets("WXDi-CP02-081");
        setLinkedImageSets("WXDi-CP02-080");

        setOriginalName("才羽ミドリ");
        setAltNames("サイバミドリ Saiba Midori");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたが手札から＜ブルアカ＞のカードを１枚以上捨てていた場合、以下の２つから１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2あなたの場に《才羽モモイ》がある場合、カードを２枚引き、手札を１枚捨てる。" +
                "~{{U：あなたのアタックフェイズ開始時、アップ状態のこのシグニをダウンしてもよい。そうした場合、カードを１枚引く。"
        );

        setName("en", "Saiba Midori");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have discarded one or more <<Blue Archive>> cards this turn, choose one of the following.\n$$1Draw a card. \n$$2If there is \"Saiba Momoi\" on your field, draw two cards and discard a card.~{{U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, draw a card."
        );
        
        setName("en_fan", "Midori Saiba");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you discarded 1 or more <<Blue Archive>> cards from your hand this turn, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 If there is \"Momori Saiba\" on your field, draw 2 cards, and discard 1 card from your hand." +
                "~{{U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, draw 1 card."
        );

		setName("zh_simplified", "才羽绿");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你从手牌把<<ブルアカ>>牌1张以上舍弃过的场合，从以下的2种选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 你的场上有《才羽モモイ》的场合，抽2张牌，手牌1张舍弃。\n" +
                "~{{U你的攻击阶段开始时，可以把竖直状态的这只精灵#D。这样做的场合，抽1张牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller()) && event.getCaller().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE)) >= 1)
            {
                if(playerChoiceMode() == 1)
                {
                    draw(1);
                } else if(new TargetFilter().own().SIGNI().withName("才羽モモイ").getValidTargetsCount() > 0)
                {
                    draw(2);
                    discard(1);
                }
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                draw(1);
            }
        }
    }
}
