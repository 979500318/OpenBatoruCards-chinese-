package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_B1_MidorikoTHEDOORPhantomBeast extends Card {

    public SIGNI_B1_MidorikoTHEDOORPhantomBeast()
    {
        setImageSets("WXDi-P15-065");

        setOriginalName("幻獣　緑子//THE DOOR");
        setAltNames("ゲンジュウミドリコザドアー Genjuu Midoriko Za Doaa");
        setDescription("jp",
                "@E @[手札から＜解放派＞のシグニを２枚まで捨てる]@：この方法で捨てたカード１枚につきカードを１枚引く。\n\n" +
                "@C：このカードの上にある＜解放派＞のシグニは@>@U：あなたのアタックフェイズ開始時、カードを１枚引く。@@を得る。"
        );

        setName("en", "Midoriko//THE DOOR, Phantom Beast");
        setDescription("en",
                "@E @[Discard up to two <<Liberation Division>> SIGNI]@: Draw a card for each card discarded this way.\n\n@C: The <<Liberation Division>> SIGNI on top of this card gains@>@U: At the beginning of your attack phase, draw a card."
        );
        
        setName("en_fan", "Midoriko//THE DOOR, Phantom Beast");
        setDescription("en_fan",
                "@E @[Discard up to 2 <<Liberation Faction>> SIGNI from your hand]@: Draw cards equal to the number of cards discarded this way.\n\n" +
                "@C: The <<Liberation Faction>> SIGNI on top of this card gains:" +
                "@>@U: At the beginning of your attack phase, draw 1 card."
        );

		setName("zh_simplified", "幻兽 绿子//THE DOOR");
        setDescription("zh_simplified", 
                "@E 从手牌把<<解放派>>精灵2张最多舍弃:依据这个方法舍弃的牌的数量，每有1张就抽1张牌。\n" +
                "@C :这张牌的上面的<<解放派>>精灵得到\n" +
                "@>@U :你的攻击阶段开始时，抽1张牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.EARTH_BEAST);
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

            registerEnterAbility(new DiscardCost(0,2, new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION)), this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
        }
        
        private void onEnterEff()
        {
            if(!getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData().get() != null)
            {
                draw(getAbility().getCostPaidData().size());
            }
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            draw(1);
        }
    }
}
