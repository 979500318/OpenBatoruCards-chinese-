package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B1_SpottedGardenEelWaterPhantom extends Card {
    
    public SIGNI_B1_SpottedGardenEelWaterPhantom()
    {
        setImageSets("WXDi-P02-066", "SPDi38-25");
        
        setOriginalName("幻水　チンアナゴ");
        setAltNames("ゲンスイチンアナゴ Gensui Chinanago");
        setDescription("jp",
                "@C：あなたの手札が４枚以上あるかぎり、このシグニのパワーは＋4000される。" +
                "~#：あなたの手札が４枚以上ある場合、対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Hassi, Phantom Aquatic Beast");
        setDescription("en",
                "@C: As long as you have four or more cards in your hand, this SIGNI gets +4000 power." +
                "~#If you have four or more cards in your hand, vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Spotted Garden Eel, Water Phantom");
        setDescription("en_fan",
                "@C: As long as there are 4 or more cards in your hand, this SIGNI gets +4000 power." +
                "~#If there are 4 or more cards in your hand, target 1 of your opponent's upped SIGNI, and banish it."
        );
        
		setName("zh_simplified", "幻水 花园鳗");
        setDescription("zh_simplified", 
                "@C :你的手牌在4张以上时，这只精灵的力量+4000。" +
                "~#你的手牌在4张以上的场合，对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            if(getHandCount(getOwner()) >= 4)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            }
        }
    }
}
