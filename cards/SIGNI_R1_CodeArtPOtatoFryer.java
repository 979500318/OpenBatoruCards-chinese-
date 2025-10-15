package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_CodeArtPOtatoFryer extends Card {
    
    public SIGNI_R1_CodeArtPOtatoFryer()
    {
        setImageSets("WXDi-P08-056");
        
        setOriginalName("コードアート　Ｐテトフライヤー");
        setAltNames("コードアートピーテトフライヤー Koodo Aato Pii Teto Furaiyaa Potato");
        setDescription("jp",
                "@C：あなたのトラッシュにスペルがあるかぎり、このシグニのパワーは＋4000される。\n" +
                "@E @[スペルを１枚捨てる]@：対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "P-tato Fryer, Code: Art");
        setDescription("en",
                "@C: As long as there is a spell in your trash, this SIGNI gets +4000 power.\n" +
                "@E @[Discard a spell]@: Vanish target SIGNI on your opponent's field with power 2000 or less."
        );
        
        setName("en_fan", "Code Art P Otato Fryer");
        setDescription("en_fan",
                "@C: As long as there is a spell in your trash, this SIGNI gets +4000 power.\n" +
                "@E @[Discard 1 spell from your hand]@: Target 1 of your opponent's SIGNI with power 2000 or less, and banish it."
        );
        
		setName("zh_simplified", "必杀代号 炸薯条机");
        setDescription("zh_simplified", 
                "@C :你的废弃区有魔法时，这只精灵的力量+4000。\n" +
                "@E 魔法1张舍弃:对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerEnterAbility(new DiscardCost(new TargetFilter().spell()), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().spell().fromTrash().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
            banish(target);
        }
    }
}
