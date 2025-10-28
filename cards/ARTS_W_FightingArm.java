package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_W_FightingArm extends Card {

    public ARTS_W_FightingArm()
    {
        setImageSets("WX24-P1-017");

        setOriginalName("ファイティング・アーム");
        setAltNames("ファイティングアーム Faitingu Aamu");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜アーム＞のシグニを１枚まで公開し手札に加え、＜アーム＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。ターン終了時まで、この方法で場に出たシグニは@>@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、このシグニをアップし、ターン終了時まで、このシグニは能力を失う。@@を得る。"
        );

        setName("en", "Fighting Arm");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 1 <<Arm>> SIGNI from among them, and add it to your hand, put up to 1 <<Arm>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Until end of turn, the SIGNI put onto the field this way gains:" +
                "@>@U: Whenever this SIGNI banishes a SIGNI in battle, up this SIGNI, and until end of turn, it loses its abilities."
        );

        setName("es", "Fighting Arm");
        setDescription("es",
                "Mira 5 cartas del tope de tu mazo y revela hasta 1 SIGNI <<Equipamiento>> de entre ellas, añadela a tu mano y pon hasta 1 SIGNI <<Equipamiento>> de entre ellas en el campo, pon el resto en el fondo del mazo en cualquier orden. Hasta el final del turno, la SIGNI puesta de esta manera en el campo gana:" +
                "@>@U: Siempre que esta SIGNI desvanezca a una SIGNI oponente en batalla, endereza esta SIGNI y hasta el final del turno, pierde sus habilidades."
        );

        setName("zh_simplified", "搏斗·武装");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<武装>>精灵1张最多公开加入手牌，<<武装>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。直到回合结束时为止，这个方法出场的精灵得到" +
                "@>@U :当这只精灵因为战斗把精灵1只破坏时，这只精灵竖直，直到回合结束时为止，这只精灵的能力失去。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            look(5);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ARM).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ARM).fromLooked().playable()).get();
            if(putOnField(cardIndex))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
            }
            
            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && !isOwnCard(caller) && getEvent().getSourceCardIndex() == getAbility().getSourceCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndexSource = getAbility().getSourceCardIndex();
            cardIndexSource.getIndexedInstance().up();
            cardIndexSource.getIndexedInstance().disableAllAbilities(cardIndexSource, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}

