package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_EthaneNaturalSource extends Card {

    public SIGNI_R2_EthaneNaturalSource()
    {
        setImageSets("WXDi-P11-061");

        setOriginalName("羅原　Ｃ２Ｈ６");
        setAltNames("ラゲンエタン Ragen Etan C2H6");
        setDescription("jp",
                "=R あなたの＜原子＞のシグニ１体の上に置く\n\n" +
                "@E：対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "C2H6, Natural Element");
        setDescription("en",
                "=R Place on top of an <<Atom>> SIGNI on your field. \n" +
                "@E: Vanish target SIGNI on your opponent's field with power 2000 or less."
        );
        
        setName("en_fan", "Ethane, Natural Source");
        setDescription("en_fan",
                "=R Put on 1 of your <<Atom>> SIGNI\n\n" +
                "@E: Target 1 of your opponent's SIGNI with power 2000 or less, and banish it."
        );

		setName("zh_simplified", "罗原 C2H6");
        setDescription("zh_simplified", 
                "=R在你的<<原子>>精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@E :对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withClass(CardSIGNIClass.ATOM));
            
            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
            banish(target);
        }
    }
}
