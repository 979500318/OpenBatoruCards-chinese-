package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.TrashCost;

import java.util.List;

public final class SIGNI_G3_AtalantaVerdantAngel extends Card {
    
    public SIGNI_G3_AtalantaVerdantAngel()
    {
        setImageSets("WXDi-D01-017");
        
        setOriginalName("翠天　アタランテ");
        setAltNames("スイテンアタランテ Suiten Atarante");
        setDescription("jp",
                "@E @[あなたのエナゾーンからそれぞれ異なるクラスを持つシグニ３枚をトラッシュに置く]@：対戦相手のパワー８０００以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Atalanta, Jade Angel");
        setDescription("en",
                "@E @[Put three SIGNI with different classes from your Ener Zone into their owner's trash]@: Vanish target SIGNI on your opponent's field with power 8000 or more."
        );
        
        setName("en_fan", "Atalanta, Verdant Angel");
        setDescription("en_fan",
                "@E @[Put 3 SIGNI with different classes from your ener zone into the trash]@: Target 1 of your opponent's SIGNI with power 8000 or more, and banish it."
        );
        
		setName("zh_simplified", "翠天 阿塔兰忒");
        setDescription("zh_simplified", 
                "@E 从能量区把持有不同类别的精灵3张放置到废弃区:对战对手的力量8000以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            EnterAbility enter = registerEnterAbility(new TrashCost(3, new TargetFilter().SIGNI().fromEner(), this::onEnterEffCostTargetCond), this::onEnterEff);
            enter.setCondition(this::onEnterEffCond);
        }
        
        private ConditionState onEnterEffCond()
        {
            return CardAbilities.getSIGNIClasses(getCardsInEner(getOwner())).size() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onEnterEffCostTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 3 && CardAbilities.getSIGNIClasses(new DataTable<>(listPickedCards)).size() >= 3;
        }
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(8000,0)).get();
            banish(cardIndex);
        }
    }
}
