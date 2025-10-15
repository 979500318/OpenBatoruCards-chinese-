package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_DajiCrimsonDevilPrincess extends Card {
    
    public SIGNI_R3_DajiCrimsonDevilPrincess()
    {
        setImageSets("WXDi-P02-037", "SPDi02-20");
        
        setOriginalName("紅魔姫　ダッキ");
        setAltNames("コウマキダッキ Koumaki Dakki");
        setDescription("jp",
                "@U $T1：あなたのライフクロス１枚がクラッシュされたとき、カードを１枚引く。\n" +
                "@U $T1：あなたのシグニ１体がコストかあなたの効果によって場からトラッシュに置かれたとき、[[エナチャージ１]]をする。\n" +
                "@E：あなたのライフクロス１枚をクラッシュしてもよい。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Daji, Crimson Evil Queen");
        setDescription("en",
                "@U $T1: When one of your Life Cloth is crushed, draw a card.\n" +
                "@U $T1: When a SIGNI on your field is put into the trash by a cost or your effect, [[Ener Charge 1]].\n" +
                "@E: You may crush one of your Life Cloth." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Daji, Crimson Devil Princess");
        setDescription("en_fan",
                "@U $T1: When 1 of your life cloth is crushed, draw 1 card.\n" +
                "@U $T1: When 1 of your SIGNI is put from the field into the trash by a cost or your effect, [[Ener Charge 1]].\n" +
                "@E: You may crush 1 of your life cloth." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );
        
		setName("zh_simplified", "红魔姬 妲己");
        setDescription("zh_simplified", 
                "@U $T1 :当你的生命护甲1张被击溃时，抽1张牌。\n" +
                "@U $T1 :当你的精灵1只因为费用或你的效果从场上放置到废弃区时，[[能量填充1]]。\n" +
                "@E :可以把你的生命护甲1张击溃。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.TRASH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            draw(1);
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.isSIGNIOnField() &&
                   getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private void onEnterEff()
        {
            if(playerChoiceActivate())
            {
                crush(getOwner());
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(1, new TargetFilter().OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
