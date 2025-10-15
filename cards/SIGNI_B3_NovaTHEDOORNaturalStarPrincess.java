package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_NovaTHEDOORNaturalStarPrincess extends Card {

    public SIGNI_B3_NovaTHEDOORNaturalStarPrincess()
    {
        setImageSets("WXDi-P15-059", "WXDi-P15-059P");

        setOriginalName("羅星姫　ノヴァ//THE DOOR");
        setAltNames("ラセイキノヴァザドアー Raseiki Nova Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に【ゲート】がある場合、カードを２枚引き、手札を１枚捨てる。\n" +
                "@U：このシグニがアタックしたとき、対戦相手は手札を１枚捨てる。このシグニと同じシグニゾーンに【ゲート】がある場合、追加で対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Nova//THE DOOR, Galactic Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is a [[Gate]] on your field, draw two cards and discard a card. \n@U: Whenever this SIGNI attacks, your opponent discards a card. If this SIGNI is in the same SIGNI Zone as a [[Gate]], in addition, your opponent discards a card."
        );
        
        setName("en_fan", "Nova//THE DOOR, Natural Star Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there is a [[Gate]] on your field, draw 2 cards, and discard 1 card from your hand.\n" +
                "@U: Whenever this SIGNI attacks, your opponent discards 1 card from their hand. If this SIGNI is on a SIGNI zone with a [[Gate]], your opponent additionally discards 1 card from their hand."
        );

		setName("zh_simplified", "罗星姬 超//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有[[大门]]的场合，抽2张牌，手牌1张舍弃。\n" +
                "@U :当这只精灵攻击时，对战对手把手牌1张舍弃。与这只精灵的相同精灵区有[[大门]]的场合，追加对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.SPACE);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0)
            {
                draw(2);
                discard(1);
            }
        }
        
        private void onAutoEff2()
        {
            discard(getOpponent(), 1);
            
            if(hasZoneObject(CardUnderType.ZONE_GATE))
            {
                discard(getOpponent(), 1);
            }
        }
    }
}
