package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_NewtonVerdantWisdomPrincess extends Card {
    
    public SIGNI_G3_NewtonVerdantWisdomPrincess()
    {
        setImageSets("WXDi-P04-039");
        
        setOriginalName("翠英姫　ニュートン");
        setAltNames("スイエイキニュートン Suieiki Nyuuton");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にいるルリグのレベルの合計が偶数の場合、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。\n" +
                "@E：あなたの場にいるルリグのレベルの合計が奇数の場合、[[エナチャージ１]]をする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2[[エナチャージ１]]"
        );
        
        setName("en", "Newton, Jade Wisdom Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if the total level of LRIG on your field is even, add up to one target SIGNI from your Ener Zone to your hand.\n" +
                "@E: If the total level of LRIG on your field is odd, [[Ener Charge 1]]." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Newton, Verdant Wisdom Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if the total level of LRIGs on your field is even, target up to 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@E: If the total level of LRIGs on your field is odd, [[Ener Charge 1]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "翠英姬 牛顿");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的分身的等级的合计在偶数的场合，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n" +
                "@E :你的场上的分身的等级的合计在奇数的场合，[[能量填充1]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().anyLRIG().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum() % 2 == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                addToHand(target);
            }
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().anyLRIG().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum() % 2 != 0)
            {
                enerCharge(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
