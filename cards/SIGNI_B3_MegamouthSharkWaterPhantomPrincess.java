package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class SIGNI_B3_MegamouthSharkWaterPhantomPrincess extends Card {

    public SIGNI_B3_MegamouthSharkWaterPhantomPrincess()
    {
        setImageSets("WX24-P4-047");

        setOriginalName("幻水姫　メガマウス");
        setAltNames("ゲンスイヒメメガマウス Gensuihime Mega Mausu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1あなたの手札が４枚以下の場合、カードを２枚引く。\n" +
                "$$2あなたの手札が５枚以上ある場合、対戦相手のシグニ１体を対象とし、手札から青のカードを２枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Megamouth Shark, Water Phantom Princess");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there are 4 or less cards in your hand, draw 2 cards.\n" +
                "$$2 If there are 5 or more cards in your hand, target 1 of your opponent's SIGNI, and you may discard 2 blue cards from your hand. If you do, until end of turn, it gets --12000 power." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "幻水姬 巨口鲨");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 你的手牌在4张以下的场合，抽2张牌。\n" +
                "$$2 你的手牌在5张以上的场合，对战对手的精灵1只作为对象，可以从手牌把蓝色的牌2张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些#D。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);

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
                if(getHandCount(getOwner()) <= 4)
                {
                    draw(2);
                }
            } else {
                if(getHandCount(getOwner()) >= 5)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                    
                    if(target != null && discard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().withColor(CardColor.BLUE)).size() == 2)
                    {
                        gainPower(target, -12000, ChronoDuration.turnEnd());
                    }
                }
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}
