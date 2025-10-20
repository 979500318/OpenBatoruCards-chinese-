package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_CodeArtYAkitoriMachine extends Card {
    
    public SIGNI_R2_CodeArtYAkitoriMachine()
    {
        setImageSets("WXDi-P04-059");
        
        setOriginalName("コードアート　Ｙキトリキ");
        setAltNames("コードアートワイキトリキ Koodo Aato Wai Kitoriki Yakitori");
        setDescription("jp",
                "@U：あなたがスペルを使用したとき、対戦相手のパワー5000以下のシグニを１体を対象とし、アップ状態のこのシグニをダウンしてもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Y - Akitoriki, Code: Art");
        setDescription("en",
                "@U: Whenever you use a spell, you may down this upped SIGNI. If you do, vanish target SIGNI with power 5000 or less on your opponent's field."
        );
        
        setName("en_fan", "Code Art Y Akitori Machine");
        setDescription("en_fan",
                "@U: Whenever you use a spell, target 1 of your opponent's SIGNI with power 5000 or less, and you may down this upped SIGNI. If you do, banish it."
        );
        
		setName("zh_simplified", "必杀代号 烤串提灯");
        setDescription("zh_simplified", 
                "@U :当你把魔法使用时，对战对手的力量5000以下的精灵1只作为对象，可以把竖直状态的这只精灵横置。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            
            if(target != null && !isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down(getCardIndex()))
            {
                banish(target);
            }
        }
    }
}
