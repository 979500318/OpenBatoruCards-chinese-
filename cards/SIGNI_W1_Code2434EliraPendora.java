package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.AbilityGain;

public final class SIGNI_W1_Code2434EliraPendora extends Card {

    public SIGNI_W1_Code2434EliraPendora()
    {
        setImageSets("WXDi-CP01-036");

        setOriginalName("コード２４３４　エリーラ ペンドラ");
        setAltNames("コードニジサンジエリーラペンドラ Koodo Nijisanji Eriira Pendora");
        setDescription("jp",
                "@E：以下の２つから１つを選ぶ。\n" +
                "$$1あなたの場に他の＜バーチャル＞のシグニがある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。\n" +
                "$$2あなたのデッキの上からカードを２枚見る。その中からカード１枚をデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Elira Pendora, Code 2434");
        setDescription("en",
                "@E: Choose one of the following.\n$$1 If there is another <<Virtual>> SIGNI on your field, target SIGNI on your opponent's field loses its abilities until end of turn.\n$$2 Look at the top two cards of your deck. Put a card on top of your deck, and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code 2434 Elira Pendora");
        setDescription("en_fan",
                "@E: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 If there is another <<Virtual>> SIGNI on your field, target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities.\n" +
                "$$2 Look at the top 2 cards of your deck. Return 1 of them to the top of your deck, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "2434代号 Elira Pendora");
        setDescription("zh_simplified", 
                "@E :从以下的2种选1种。\n" +
                "$$1 你的场上有其他的<<バーチャル>>精灵的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n" +
                "$$2 从你的牌组上面看2张牌。从中把1张牌返回牌组最上面，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(playerChoiceMode() == 1)
            {
                if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex()).getValidTargetsCount() > 0)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
                    disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
                }
            } else {
                look(2);
                
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
    }
}
