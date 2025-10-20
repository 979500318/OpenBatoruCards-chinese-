package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_BG_ButterflyEffect extends Card {

    public ARTS_BG_ButterflyEffect()
    {
        setImageSets("WX24-P4-006","WX24-P4-006U");

        setOriginalName("バタフライ・エフェクト");
        setAltNames("バタフライエフェクト Batafurai Efekuto");
        setDescription("jp",
                "対戦相手のルリグ１体を対象とし、それをダウンする。このターン、次にあなたがそれより低いレベルを持つ対戦相手のシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Butterfly Effect");
        setDescription("en",
                "Target 1 of your opponent's LRIG, and down it. This turn, the next time you would be damaged by a SIGNI with a lower level than that LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "化蝶·效应");
        setDescription("zh_simplified", 
                "对战对手的分身1只作为对象，将其横置。这个回合，下一次你因为持有比其的等级低的对战对手的精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE, CardColor.GREEN);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.color(CardColor.GREEN, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().anyLRIG()).get();
            down(target);
            
            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()) && cardIndexSnapshot.getLevel().getValue() < target.getIndexedInstance().getLevel().getValue());
        }
    }
}
