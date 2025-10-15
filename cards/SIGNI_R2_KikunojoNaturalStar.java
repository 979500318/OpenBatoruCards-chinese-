package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R2_KikunojoNaturalStar extends Card {

    public SIGNI_R2_KikunojoNaturalStar()
    {
        setImageSets("WXDi-P09-059");

        setOriginalName("羅星　キクノジョー");
        setAltNames("ラセイキクノジョー Rasei Kikunojoo");
        setDescription("jp",
                "@A $T1 @[手札からレベル１のシグニを１枚捨てる]@：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "KikunoJoe, Natural Planet");
        setDescription("en",
                "@A $T1 @[Discard a level one SIGNI]@: Vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Aoi Fuji, Natural Plant");
        setDescription("en_fan",
                "@A $T1 @[Discard 1 level 1 SIGNI from your hand]@: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );

		setName("zh_simplified", "罗星 囧囧菊");
        setDescription("zh_simplified", 
                "@A $T1 从手牌把等级1的精灵1张舍弃:对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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

            ActionAbility act = registerActionAbility(new DiscardCost(new TargetFilter().SIGNI().withLevel(1)), this::onActionEff);
            act.setUseLimit(AbilityConst.UseLimit.TURN, 1);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
    }
}
