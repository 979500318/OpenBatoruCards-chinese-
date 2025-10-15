package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K2_GuzukoAppeal extends Card {

    public LRIGA_K2_GuzukoAppeal()
    {
        setImageSets("WXDi-P14-037");

        setOriginalName("グズ子～アピール～");
        setAltNames("グズコアピール Guzuko Apiiru");
        setDescription("jp",
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。その後、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをこの方法で場に出たシグニのパワーと同じだけ－（マイナス）する。"
        );

        setName("en", "Guzuko ~Appeal~");
        setDescription("en",
                "@E: Put target SIGNI from your trash onto your field. Then, target SIGNI on your opponent's field gets -- power equal to the power of the SIGNI put onto the field this way until end of turn."
        );
        
        setName("en_fan", "Guzuko~Appeal~");
        setDescription("en_fan",
                "@E: Target 1 SIGNI from your trash, and put it onto the field. Then, target 1 of your opponent's SIGNI, and until end of turn, it gets -- (minus) power equal to the power of the SIGNI that entered the field this way."
        );

		setName("zh_simplified", "迟钝子～恳求～");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把精灵1张作为对象，将其出场。然后，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-（减号）与这个方法出场的精灵的力量相同的数值。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            
            if(putOnField(target))
            {
                CardIndex targetOP = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(targetOP, -target.getIndexedInstance().getPower().getValue(), ChronoDuration.turnEnd());
            }
        }
    }
}
