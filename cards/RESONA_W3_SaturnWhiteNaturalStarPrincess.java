package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PlayerRuleBaseValueModifier;

public final class RESONA_W3_SaturnWhiteNaturalStarPrincess extends Card {

    public RESONA_W3_SaturnWhiteNaturalStarPrincess()
    {
        setImageSets("WXDi-P11-TK01");

        setOriginalName("白羅星姫　サタン");
        setAltNames("ハクラセイキサタン Hakuraseiki Satan");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計２枚トラッシュに置く\n\n" +
                "@C：あなたのターンの間、対戦相手はシグニを２体までしか場に出すことができない。"
        );

        setName("en", "Saturn, White Planet Queen");
        setDescription("en",
                "Put two SIGNI from your hand and/or Ener Zone into your trash.\n\n" +
                "@C: During your turn, your opponent can only have up to two SIGNI on their field."
        );
        
        setName("en_fan", "Saturn, White Natural Star Princess");
        setDescription("en_fan",
                "Put 2 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@C: During your turn, your opponent can only have up to 2 SIGNI on the field."
        );
        
		setName("zh_simplified", "白罗星姬 土星");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计2张放置到废弃区\n" +
                "@C :你的回合期间，对战对手只能有精灵2只最多出场。（场上已经有3只的场合，对战对手把精灵放置到废弃区，变为2只）\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RESONA, 2, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            registerConstantAbility(this::onConstEffCond, new PlayerRuleBaseValueModifier(PlayerRuleValueType.MAX_ALLOWED_SIGNI, TargetFilter.HINT_OWNER_OP, 2));
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
    }
}

