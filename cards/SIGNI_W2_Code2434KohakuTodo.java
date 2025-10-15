package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W2_Code2434KohakuTodo extends Card {

    public SIGNI_W2_Code2434KohakuTodo()
    {
        setImageSets("WXDi-CP01-039");

        setOriginalName("コード２４３４　東堂コハク");
        setAltNames("コードニジサンジトウドウコハク Koodo Nijisanji Toudou Kohaku");
        setDescription("jp",
                "@E %W：あなたの場に他の＜バーチャル＞のシグニがある場合、対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Todo Kohaku, Code 2434");
        setDescription("en",
                "@E %W: If there is another <<Virtual>> SIGNI on your field, return target level one SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Code 2434 Kohaku Todo");
        setDescription("en_fan",
                "@E %W: If there is another <<Virtual>> SIGNI on your field, target 1 of your opponent's level 1 SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "2434代号 东堂琥珀");
        setDescription("zh_simplified", 
                "@E %W:你的场上有其他的<<バーチャル>>精灵的场合，对战对手的等级1的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL, CardSIGNIClass.SELENE_GIRLS_ACADEMY);
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

            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
                addToHand(target);
            }
        }
    }
}
