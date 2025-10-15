package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_B1_TamagoFessoneNaturalStar extends Card {

    public SIGNI_B1_TamagoFessoneNaturalStar()
    {
        setImageSets("WXDi-P16-071");

        setOriginalName("羅星　タマゴ//フェゾーネ");
        setAltNames("ラセイタマゴフェゾーネ Rasei Tamago Fezoone");
        setDescription("jp",
                "@A #D：対戦相手のシグニを２体まで対象とし、それらを凍結する。\n" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Tamago//Fesonne, Natural Planet");
        setDescription("en",
                "@A #D: Freeze up to two target SIGNI on your opponent's field." +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card."
        );
        
        setName("en_fan", "Tamago//Fessone, Natural Star");
        setDescription("en_fan",
                "@A #D: Target up to 2 of your opponent's SIGNI, and freeze them." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "罗星 玉子//音乐节");
        setDescription("zh_simplified", 
                "@A #D:对战对手的精灵2只最多作为对象，将这些冻结。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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

            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            freeze(data);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            discard(getOpponent(), 1);
        }
    }
}
