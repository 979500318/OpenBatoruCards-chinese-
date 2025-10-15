package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.CardAbilities;

public final class LRIGA_K2_IonaTheBeam extends Card {

    public LRIGA_K2_IonaTheBeam()
    {
        setImageSets("WXDi-P13-036");

        setOriginalName("イオナ・ザ・ビーム");
        setAltNames("イオナザビーム Iona Za Biimu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。あなたの場にあるシグニが持つ色が合計２種類以上ある場合、代わりにそれをトラッシュに置く。"
        );

        setName("en", "Iona the Beam");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field. If you have two or more different colors among SIGNI on your field, instead put it into its owner's trash."
        );
        
        setName("en_fan", "Iona The Beam");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it. If there are 2 or more colors among SIGNI on your field, put it into the trash instead."
        );

		setName("zh_simplified", "伊绪奈·死·光束");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。你的场上的精灵持有颜色合计2种类以上的场合，作为替代，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            
            if(CardAbilities.getColorsCount(getSIGNIOnField(getOwner())) < 2)
            {
                banish(target);
            } else {
                trash(target);
            }
        }
    }
}
