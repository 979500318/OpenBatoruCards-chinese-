package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W3_FionnMacCumhaillHolyGeneral extends Card {

    public SIGNI_W3_FionnMacCumhaillHolyGeneral()
    {
        setImageSets("WXDi-P09-054");

        setOriginalName("聖将　フィン・マックール");
        setAltNames("セイショウフィンマックール Seishou Fion Makkuuru Finn McCool Finn MacCool");
        setDescription("jp",
                "@U：あなたのターン終了時、アップ状態のこのシグニをダウンしてもよい。そうした場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、それをトラッシュに置く。\n" +
                "$$2カードを１枚引く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Fionn mac Cumhaill, Blessed General");
        setDescription("en",
                "@U: At the end of your turn, you may down this upped SIGNI. If you do, choose one of the following.\n" +
                "$$1 Put target SIGNI on your opponent's field into its owner's trash.\n" +
                "$$2 Draw a card." +
                "~#Choose one -- \n$$1 Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Fionn mac Cumhaill, Holy General");
        setDescription("en_fan",
                "@U: At the end of your turn, you may down this upped SIGNI. If you do, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and put it into the trash.\n" +
                "$$2 Draw 1 card." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "圣将 芬恩·麦克库尔");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，可以把竖直状态的这只精灵横置。这样做的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，将其放置到废弃区。\n" +
                "$$2 抽1张牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(12000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                    trash(target);
                } else {
                    draw(1);
                }
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
