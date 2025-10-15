package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_Code2434ChigusaNishizono extends Card {

    public SIGNI_W3_Code2434ChigusaNishizono()
    {
        setImageSets("WXDi-CP01-028");

        setOriginalName("コード２４３４　西園チグサ");
        setAltNames("コードニジサンジニシゾノチグサ Koodo Nijisanji Nishizono Chigusa");
        setDescription("jp",
                "@U $T1：あなたのルリグ１体がアタックしたとき、あなたの場に＜バーチャル＞のシグニが３体ある場合、%W %Wを支払ってもよい。そうした場合、そのルリグをアップし、ターン終了時まで、そのルリグは能力を失う。\n" +
                "@E %W：あなたのデッキの上からカードを３枚見る。その中から＜バーチャル＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Nishizono Chigusa, Code 2434");
        setDescription("en",
                "@U $T1: When a LRIG on your field attacks, if there are three <<Virtual>> SIGNI on your field, you may pay %W %W. If you do, up the LRIG that attacked and it loses its abilities until end of turn.\n@E %W: Look at the top three cards of your deck. Reveal a <<Virtual>> SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code 2434 Chigusa Nishizono");
        setDescription("en_fan",
                "@U $T1: When your LRIG attacks, if there are 3 <<Virtual>> SIGNI on your field, you may pay %W %W. If you do, up that LRIG, and until end of turn, it loses its abilities.\n" +
                "@E %W: Look at the top 3 cards of your deck. Reveal 1 <<Virtual>> SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "2434代号 西园千草");
        setDescription("zh_simplified", 
                "@U $T1 :当你的分身1只攻击时，你的场上的<<バーチャル>>精灵在3只的场合，可以支付%W %W。这样做的场合，那只分身竖直，直到回合结束时为止，那只分身的能力失去。\n" +
                "@E %W:从你的牌组上面看3张牌。从中把<<バーチャル>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL, CardSIGNIClass.SELENE_GIRLS_ACADEMY);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).getValidTargetsCount() >= 3 &&
               payEner(Cost.color(CardColor.WHITE, 2)))
            {
                up(caller);
                disableAllAbilities(caller, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
