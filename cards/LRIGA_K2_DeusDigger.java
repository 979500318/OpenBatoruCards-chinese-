package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;

public final class LRIGA_K2_DeusDigger extends Card {
    
    public LRIGA_K2_DeusDigger()
    {
        setImageSets("WXDi-P04-024");
        
        setOriginalName("デウスディガー");
        setAltNames("Deusu Digaa");
        setDescription("jp",
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。\n" +
                "@E：あなたのシグニ１体を対象とし、あなたのルリグトラッシュからルリグ１枚をそれの[[ソウル]]にする。"
        );
        
        setName("en", "Deus Digger");
        setDescription("en",
                "@E: Put target SIGNI from your trash onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@E: Attach a LRIG from your LRIG Trash to target SIGNI on your field as a [[Soul]]."
        );
        
        setName("en_fan", "Deus Digger");
        setDescription("en_fan",
                "@E: Target 1 SIGNI without #G @[Guard]@ from your trash, and put it onto the field. Its @E abilities don't activate.\n" +
                "@E: Target 1 of your SIGNI, and attach 1 LRIG from your LRIG trash to it as a [[Soul]]."
        );
        
		setName("zh_simplified", "迪乌斯挖掘");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n" +
                "@E :你的精灵1只作为对象，从你的分身废弃区把分身1张作为其的[[灵魂]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DEUS);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).playable().fromTrash()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().anyLRIG().fromTrash(DeckType.LRIG)).get();
                attach(target, cardIndex, CardUnderType.ATTACHED_SOUL);
            }
        }
    }
}
