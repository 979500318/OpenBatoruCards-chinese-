package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_CodeArtKEyboard extends Card {

    public SIGNI_K2_CodeArtKEyboard()
    {
        setImageSets("WX25-P2-105");

        setOriginalName("コードアート　Kボード");
        setAltNames("コードアートケーボード Koodo Aato Keeboodo Keyboard");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1あなたの場に青の＜電機＞のシグニがある場合、対戦相手のデッキの上からカードを４枚トラッシュに置く。\n" +
                "$$2このターンにあなたがスペルを使用していた場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Code Art K Eyboard");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there is a blue <<Electric Machine>> SIGNI on your field, put the top 4 cards of your opponent's deck into the trash.\n" +
                "$$2 If you used a spell this turn, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "必杀代号 键盘");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 你的场上有蓝色的<<電機>>精灵的场合，从对战对手的牌组上面把4张牌放置到废弃区。\n" +
                "$$2 这个回合你把魔法使用过的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(playerChoiceMode() == 1)
            {
                if(new TargetFilter().own().SIGNI().withColor(CardColor.BLUE).withClass(CardSIGNIClass.ELECTRIC_MACHINE).getValidTargetsCount() > 0)
                {
                    millDeck(getOpponent(), 4);
                }
            } else if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller())) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
